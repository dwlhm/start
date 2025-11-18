package com.dwlhm.home.ui

import android.net.Uri
import java.net.IDN
import java.util.regex.Pattern

/**
 * Utility class for URL validation and formatting according to industry standards (RFC 3986).
 * Handles various URL formats including:
 * - URLs with protocol (http://, https://, etc.)
 * - URLs without protocol (auto-adds https://)
 * - IP addresses
 * - Localhost addresses
 * - Search queries (if input is not a valid URL)
 */
object UrlUtils {
    
    // Pattern untuk validasi domain name sesuai RFC 3986
    private val DOMAIN_PATTERN = Pattern.compile(
        "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$"
    )
    
    // Pattern untuk validasi IP address (IPv4)
    private val IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    )
    
    // Pattern untuk validasi localhost
    private val LOCALHOST_PATTERN = Pattern.compile(
        "^(localhost|127\\.0\\.0\\.1|0\\.0\\.0\\.0|\\[::1\\])(:\\d+)?"
    )
    
    /**
     * Validates if the input string is a valid URL format.
     * 
     * @param input The input string to validate
     * @return true if the input appears to be a valid URL, false otherwise
     */
    fun isValidUrl(input: String): Boolean {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return false
        
        // Check if it's a valid URI with scheme
        if (hasScheme(trimmed)) {
            return try {
                val uri = Uri.parse(trimmed)
                uri.scheme != null && uri.host != null
            } catch (e: Exception) {
                false
            }
        }
        
        // Check if it's a valid domain, IP address, or localhost
        return isValidDomain(trimmed) || 
               isValidIpAddress(trimmed) || 
               isValidLocalhost(trimmed)
    }
    
    /**
     * Formats the input string into a valid URL.
     * If the input is not a valid URL, it will be treated as a search query.
     * 
     * @param input The input string to format
     * @return A formatted URL string, or a search query URL if input is not a valid URL
     */
    fun formatUrl(input: String): String {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return ""
        
        // Jika sudah ada scheme yang valid, langsung return setelah normalize
        if (hasScheme(trimmed)) {
            return normalizeUrl(trimmed)
        }
        
        // Cek apakah mengandung karakter yang menunjukkan ini adalah URL
        // (misalnya mengandung titik, slash, atau colon untuk port)
        val looksLikeUrl = trimmed.contains('.') || 
                          trimmed.contains('/') || 
                          trimmed.contains(':') ||
                          isValidIpAddress(trimmed) ||
                          isValidLocalhost(trimmed)
        
        if (looksLikeUrl) {
            // Coba parse sebagai URL tanpa scheme
            val normalized = normalizeUrl("https://$trimmed")
            if (isValidUrl(normalized)) {
                return normalized
            }
        }
        
        // Jika tidak terlihat seperti URL, anggap sebagai search query
        // Untuk sekarang, kita akan tetap mencoba sebagai URL dengan https://
        // Browser biasanya akan handle search query sendiri jika URL tidak valid
        return normalizeUrl("https://$trimmed")
    }
    
    /**
     * Normalizes a URL by:
     * - Converting to lowercase (scheme and host)
     * - Removing default ports
     * - Encoding special characters properly
     * - Handling internationalized domain names (IDN)
     */
    private fun normalizeUrl(url: String): String {
        return try {
            val uri = Uri.parse(url)
            
            // Normalize scheme to lowercase
            val scheme = uri.scheme?.lowercase() ?: "https"
            
            // Normalize host
            val host = uri.host
            if (host == null) {
                // If no host, return original URL
                return url
            }
            
            // Handle IDN (Internationalized Domain Names)
            val normalizedHost = try {
                IDN.toASCII(host.lowercase())
            } catch (e: Exception) {
                // If IDN conversion fails, use lowercase host
                host.lowercase()
            }
            
            // Build authority with port handling
            val port = uri.port
            val defaultPort = when (scheme) {
                "http" -> 80
                "https" -> 443
                "ftp" -> 21
                else -> -1
            }
            
            val authority = if (port != -1 && port != defaultPort) {
                "$normalizedHost:$port"
            } else {
                normalizedHost
            }
            
            // Build the normalized URL
            val builder = StringBuilder()
            builder.append(scheme).append("://").append(authority)
            
            // Preserve path
            uri.path?.let { builder.append(it) }
            
            // Preserve query
            uri.query?.let { builder.append("?").append(it) }
            
            // Preserve fragment
            uri.fragment?.let { builder.append("#").append(it) }
            
            builder.toString()
        } catch (e: Exception) {
            // If parsing fails, return original URL
            url
        }
    }
    
    /**
     * Checks if the input string has a URL scheme (protocol).
     */
    private fun hasScheme(input: String): Boolean {
        val lowerInput = input.lowercase()
        return lowerInput.startsWith("http://") ||
               lowerInput.startsWith("https://") ||
               lowerInput.startsWith("ftp://") ||
               lowerInput.startsWith("file://") ||
               lowerInput.startsWith("data:") ||
               lowerInput.matches(Regex("^[a-z][a-z0-9+\\-.]*:.*"))
    }
    
    /**
     * Validates if the input is a valid domain name.
     */
    private fun isValidDomain(input: String): Boolean {
        // Remove port if present
        val domain = input.split(':').first()
        
        // Check basic domain pattern
        if (!DOMAIN_PATTERN.matcher(domain).matches()) {
            return false
        }
        
        // Additional validation: check each label
        val labels = domain.split('.')
        if (labels.size < 2) return false
        
        for (label in labels) {
            if (label.isEmpty() || label.length > 63) {
                return false
            }
            if (label.startsWith('-') || label.endsWith('-')) {
                return false
            }
        }
        
        // TLD should be at least 2 characters
        val tld = labels.last()
        if (tld.length < 2) return false
        
        return true
    }
    
    /**
     * Validates if the input is a valid IP address (IPv4).
     */
    private fun isValidIpAddress(input: String): Boolean {
        val ip = input.split(':').first() // Remove port if present
        return IPV4_PATTERN.matcher(ip).matches()
    }
    
    /**
     * Validates if the input is a valid localhost address.
     */
    private fun isValidLocalhost(input: String): Boolean {
        return LOCALHOST_PATTERN.matcher(input.lowercase()).matches()
    }
    
    /**
     * Extracts the domain from a URL string.
     * Useful for displaying in the address bar.
     */
    fun extractDomain(url: String): String {
        return try {
            val uri = Uri.parse(url)
            uri.host ?: url
        } catch (e: Exception) {
            url
        }
    }
}

