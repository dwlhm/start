import { defineConfig } from "@pandacss/dev";

export default defineConfig({
  preflight: true,
  include: ["./src/**/*.{js,jsx,ts,tsx}"],
  exclude: [],
  outdir: "styled-system",
  jsxFramework: "react",
  strictTokens: true,
  strictPropertyValues: true,
  globalCss: {
    html: {
      colorScheme: "light dark",
      lineHeight: 1.5,
      textRendering: "optimizeLegibility",
      WebkitFontSmoothing: "antialiased",
      MozOsxFontSmoothing: "grayscale",
    },
    body: {
      margin: 0,
      fontFamily: "var(--fonts-body)",
      bg: "white",
      color: "black",
    },
    "*, *::before, *::after": {
      borderColor: "muted.50",
    },
  },

  theme: {
    extend: {
      tokens: {
        colors: {
          primary: {
            0: { value: "#1ADBBB" },
            50: { value: "#15B097" },
            100: { value: "#13A48C" },
            200: { value: "#0F806D" },
          },
          black: {
            value: "#000009",
          },
          white: {
            value: "#FEFFEA",
          },
          muted: {
            50: { value: "#C9C9C9" },
            100: { value: "#A0A0A0" },
            200: { value: "#777777" },
          },
          error: {
            50: { value: "#D44D5C" },
            100: { value: "#C23A4A" },
            200: { value: "#B02738" },
          },
          success: {
            50: { value: "#628B48" },
            100: { value: "#5A7E40" },
            200: { value: "#527138" },
          },
          warning: {
            50: { value: "#EE6C4D" },
            100: { value: "#E55A3B" },
            200: { value: "#DC4829" },
          },
          gradients: {
            hero: {
              value:
                "linear-gradient(to bottom, #0f172a, #1e293b, #0f172a)",
            },
            heroOverlay: {
              value:
                "linear-gradient(to right, rgba(6, 182, 212, 0.1), rgba(59, 130, 246, 0.1), rgba(168, 85, 247, 0.1))",
            },
            text: {
              value: "linear-gradient(to right, #22d3ee, #60a5fa)",
            },
          },
        },
        fonts: {
          body: {
            value:
              "-apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif",
          },
          mono: {
            value:
              "source-code-pro, Menlo, Monaco, Consolas, 'Courier New', monospace",
          },
        },
        spacing: {
          xs: { value: "0.5rem" },
          sm: { value: "0.75rem" },
          md: { value: "1rem" },
          lg: { value: "1.5rem" },
          xl: { value: "2rem" },
        },
        radii: {
          xs: { value: "0.125rem" },
          sm: { value: "0.25rem" },
          md: { value: "0.375rem" },
          lg: { value: "0.5rem" },
          xl: { value: "0.75rem" },
          "2xl": { value: "1rem" },
          full: { value: "9999px" },
        },
      },
      semanticTokens: {
        colors: {
          boxShadow: {
            default: {
              value: {
                base: "0 10px 15px -3px rgba(6, 182, 212, 0.3)",
              },
            },
          },
        },
      },
    },
  },

  conditions: {
    extend: {
      light: "[data-theme=light] &, .light &",
      dark: "[data-theme=dark] &, .dark &",
    },
  },
});
