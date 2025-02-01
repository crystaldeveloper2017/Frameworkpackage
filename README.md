## Setup SKPSecurityGateV2

Follow these steps to set up the project:

```bash
(set -e  # Exit immediately if a command exits with a non-zero status

# Variables
REPO_URL="https://github.com/crystaldeveloper2017/SKPSecurityGateV2"
PROJECT_DIR="SKPSecurityGateV2"
CONFIG_FILE_PATH="$PROJECT_DIR/Config.yaml"

# Clone the repository with submodules
echo "Cloning the repository..."
git clone --recurse-submodules $REPO_URL || { echo "Failed to clone the repository. Exiting."; exit 1; }

echo "Repository cloned successfully."

# Ensure directory structure exists
echo "Creating directory structure..."
mkdir -p "$(dirname "$CONFIG_FILE_PATH")"

# Create the Config.yaml file with the configuration
echo "Creating Config.yaml file..."
cat > "$CONFIG_FILE_PATH" <<EOL
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

echo "Setup complete. You can now run the project."
