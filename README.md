#!/bin/bash

# Variables
REPO_URL="https://github.com/crystaldeveloper2017/SKPSecurityGateV2"
PROJECT_DIR="SKPSecurityGateV2"
CONFIG_FILE_PATH="$PROJECT_DIR/Config.yaml"

# Clone the repository with submodules
echo "Cloning the repository..."
git clone --recurse-submodules $REPO_URL

# Check if the repository was cloned successfully
if [ -d "$PROJECT_DIR" ]; then
    echo "Repository cloned successfully."
else
    echo "Failed to clone the repository. Exiting."
    exit 1
fi

# Create directories if they don't exist
echo "Creating directory structure..."
mkdir -p "$(dirname "$CONFIG_FILE_PATH")"

# Create the Config.yaml file with the configuration
echo "Creating Config.yaml file..."
cat <<EOL > $CONFIG_FILE_PATH
mysqlusername: ""
password: ""
host: "localhost"
port: "3306"
mySqlPath: "1"
schemaName: skpsecuritygate
projectName: skpsecuritygate maven
thread_sleep: 0
isAuditEnabled: "true"
copyAttachmentsToBuffer: "false"
persistentPath: "/home/ubuntu/skpsecuritygate_attachments/"
queryLogEnabled: "false"
sendEmail: "false"
EOL

echo "Config.yaml file created at $CONFIG_FILE_PATH."

# Done
echo "Setup complete. You can now run the project."
