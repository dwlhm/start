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
            50: { value: "#B1EDE8" },
            100: { value: "#9AE5DE" },
            200: { value: "#83DDD4" },
          },
          secondary: {
            50: { value: "#FAC9B8" },
            100: { value: "#F8B8A3" },
            200: { value: "#F6A78E" },
          },
          accent: {
            50: { value: "#FCFF4B" },
            100: { value: "#FBFF33" },
            200: { value: "#FAFF1B" },
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
                base: "{colors.primary.50}",
                _hover: "{colors.primary.100}",
                _active: "{colors.primary.200}",
              },
            },
            surface: {
              value: {
                base: "{colors.secondary.50}",
              },
            },
            secondary: {
              value: {
                base: "{colors.secondary.50}",
                _hover: "{colors.secondary.100}",
                _active: "{colors.secondary.200}",
              },
            },
            muted: {
              value: {
                base: "{colors.muted.50}",
              },
            },
            error: {
              value: {
                base: "{colors.error.50}",
                _hover: "{colors.error.100}",
                _active: "{colors.error.200}",
              },
            },
            success: {
              value: {
                base: "{colors.success.50}",
                _hover: "{colors.success.100}",
                _active: "{colors.success.200}",
              },
            },
            warning: {
              value: {
                base: "{colors.warning.50}",
                _hover: "{colors.warning.100}",
                _active: "{colors.warning.200}",
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
                base: "{colors.primary.200}",
              },
            },
            secondary: {
              value: {
                base: "{colors.secondary.200}",
              },
            },
            muted: {
              value: {
                base: "{colors.muted.200}",
              },
            },
            error: {
              value: {
                base: "{colors.error.100}",
              },
            },
            success: {
              value: {
                base: "{colors.success.100}",
              },
            },
            warning: {
              value: {
                base: "{colors.warning.100}",
              },
            },
            white: {
              value: {
                base: "{colors.white}",
              },
            },
            black: {
              value: {
                base: "{colors.black}",
              },
            },
          },
          border: {
            default: {
              value: {
                base: "{colors.primary.100}",
              },
            },
            muted: {
              value: {
                base: "{colors.muted.100}",
              },
            },
            error: {
              value: {
                base: "{colors.error.100}",
              },
            },
            success: {
              value: {
                base: "{colors.success.100}",
              },
            },
            warning: {
              value: {
                base: "{colors.warning.100}",
              },
            },
          },
          accent: {
            default: {
              value: {
                base: "{colors.accent.50}",
                _hover: "{colors.accent.100}",
                _active: "{colors.accent.200}",
              },
            },
            subtle: {
              value: {
                base: "{colors.accent.50}",
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
