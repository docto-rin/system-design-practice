#!/bin/bash

# Check if arguments are provided
if [ -z "$1" ]; then
  echo "Usage: ./start_problem.sh <Problem Name>"
  echo "Example: ./start_problem.sh LRU Cache"
  exit 1
fi

# Combine all arguments to form the PR title (e.g., ./start_problem.sh LRU Cache -> "LRU Cache")
PR_TITLE="$*"

# Generate slug: Convert to lowercase and replace spaces with hyphens (e.g., "LRU Cache" -> "lru-cache")
SLUG=$(echo "$PR_TITLE" | tr '[:upper:]' '[:lower:]' | tr ' ' '-')

# Generate Java package name: Replace hyphens with underscores (e.g., "lru-cache" -> "lru_cache")
PACKAGE_NAME=$(echo "$SLUG" | tr '-' '_')

# Configuration paths
BASE_PKG="io.github.docto_rin"
BASE_DIR="app/src/main/java/io/github/docto_rin"
TEST_DIR="app/src/test/java/io/github/docto_rin"
GRADLE_FILE="app/build.gradle.kts"

echo "🚀 Starting setup for: $PR_TITLE"
echo "   - Branch/Slug: $SLUG"
echo "   - Java Package: $PACKAGE_NAME"

# 1. Create and switch to a new Git branch
git checkout main
git pull origin main
git checkout -b "$SLUG"

# 2. Create directories for source and tests
mkdir -p "$BASE_DIR/$PACKAGE_NAME"
mkdir -p "$TEST_DIR/$PACKAGE_NAME"

# 3. Create Main.java with boilerplate code
cat <<EOF > "$BASE_DIR/$PACKAGE_NAME/Main.java"
package ${BASE_PKG}.${PACKAGE_NAME};

public class Main {
    public static void main(String[] args) {
        System.out.println("Running solution for: ${PR_TITLE}");
    }
}
EOF

# 4. Update mainClass in build.gradle.kts to point to the new Main class
NEW_MAIN_CLASS="${BASE_PKG}.${PACKAGE_NAME}.Main"
sed -i '' "s|PLACEHOLDER|${NEW_MAIN_CLASS}|" "$GRADLE_FILE"

# 5. Commit and Push
git add .
git commit -m "Init $PR_TITLE"
git push -u origin "$SLUG"

# 6. Create Pull Request using GitHub CLI
echo "Creating Draft PR..."
gh pr create \
  --draft \
  --title "$PR_TITLE" \
  --body "Problem Link: https://github.com/ashishps1/awesome-low-level-design/blob/main/problems/${SLUG}.md" \
  --base main

echo "✅ Setup complete for $PR_TITLE"