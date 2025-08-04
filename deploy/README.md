# Linktag Deployment Setup

This guide explains how to set up the Linktag application as a systemd service on Linux.

1. Copy the `deploy/linktag.service` file to the systemd service directory:

    ```bash
    sudo cp deploy/linktag.service /etc/systemd/system/
    ```

2. Reload systemd and enable the service to start automatically on boot:

    ```bash
    sudo systemctl daemon-reload
    sudo systemctl enable linktag.service
    ```

3. Start the Linktag service and verify that it’s running:

    ```bash
    sudo systemctl start linktag.service
    sudo systemctl status linktag.service
    ```
