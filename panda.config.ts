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
      bg: "bg.canvas",
      color: "text.primary",
    },
    "*, *::before, *::after": {
      borderColor: "border.default",
    },
  },

  theme: {
    extend: {
      tokens: {
        colors: {
          primary: {
            value: "#0f172a",
          },
          secondary: {
            value: "#64748b",
          },
          muted: {
            value: "#94a3b8",
          },
          error: {
            value: "#dc2626",
          },
          success: {
            value: "#16a34a",
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
          "2xl": { value: "3rem" },
          "3xl": { value: "4rem" },
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
      semanticTokens: {
        colors: {
          bg: {
            canvas: {
              value: {
                base: "{colors.white}",
              },
            },
            primary: {
              value: {
                base: "{colors.primary}",
              },
            },
            surface: {
              value: {
                base: "{colors.secondary}",
              },
            },
            secondary: {
              value: {
                base: "{colors.secondary}",
              },
            },
            muted: {
              value: {
                base: "{colors.muted}",
              },
            },
            gradient_hero: {
              value: {
                base: "{gradients.hero}",
              },
            },
            gradient_hero_overlay: {
              value: {
                base: "{gradients.heroOverlay}",
              },
            },
            gradient_text: {
              value: {
                base: "{gradients.text}",
              },
            },
          },
          text: {
            primary: {
              value: {
                base: "{colors.primary}",
              },
            },
            secondary: {
              value: {
                base: "{colors.secondary}",
              },
            },
            muted: {
              value: {
                base: "{colors.muted}",
              },
            },
            error: {
              value: {
                base: "{colors.error}",
              },
            },
            success: {
              value: {
                base: "{colors.success}",
              },
            },
            white: {
              value: {
                base: "{colors.white}",
              },
            },
          },
          border: {
            default: {
              value: {
                base: "{colors.primary}",
              },
            },
            muted: {
              value: {
                base: "{colors.muted}",
              },
            },
          },
          accent: {
            default: {
              value: {
                base: "{colors.blue.500}",
              },
            },
            subtle: {
              value: {
                base: "{colors.blue.50}",
              },
            },
          },
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

  patterns: {
    extend: {
      // Add custom patterns if needed
    },
  },

  // Best Practice: Use utility classes for better performance
  utilities: {
    extend: {
      // Add custom utilities if needed
    },
  },
});
