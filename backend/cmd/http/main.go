package main

import (
	"os"
	"time"

	"github.com/gofiber/fiber/v2"
)

var version = "dev"

func main() {
	app := fiber.New()
	startTime := time.Now()

	if v := os.Getenv("VERSION"); v != "" {
		version = v
	}

	app.Get("/", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{
			"name":      "user-service",
			"version":   version,
			"status":    "running",
			"message":   "User service is running",
			"timestamp": time.Now().Format(time.RFC3339),
			"uptime":    time.Since(startTime).String(),
		})
	})
	app.Listen(":3000")
}
