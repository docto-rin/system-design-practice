# Tic Tac Toe

## 1. Requirements Clarification

### Functional Requirements

> 1. The Tic-Tac-Toe game should be played on a 3x3 grid.
> 2. Two players take turns marking their symbols (X or O) on the grid.
> 3. The first player to get three of their symbols in a row (horizontally, vertically, or diagonally) wins the game.
> 4. If all the cells on the grid are filled and no player has won, the game ends in a draw.
> 5. The game should have a user interface to display the grid and allow players to make their moves.
> 6. The game should handle player turns and validate moves to ensure they are legal.
> 7. The game should detect and announce the winner or a draw at the end of the game.

### Non-Functional Requirements

- 標準入出力を使う。
- 盤面（グリッド）もテキストで表現する。
- 盤面は1,2,3行とa,b,c列で、2cなどで座標を表し、ユーザーに入力させる。

### Constraints and Assumptions

- Java 21で実装する。
- コンソール上でテキストでのUI
- 人間2人が単一のローカルマシンで対戦する。
- ボードサイズは3x3のみ。
- 対戦履歴などの機能は一旦なし。

## 2. Entity Identification

### Core Entities

- Player
  - 責任：プレイヤーの情報を管理し提供する。
  - データ：
    - id：1P or 2P
    - name：最初に入力させる
- Board
  - 責任：現在の3x3盤面の管理と更新依頼の検証、更新の実行
  - データ：
    - grid：3x3の状況を管理
- Symbol
  - 責任：grid上に置かれる記号の種類。
  - データ：
    - O、X、EMPTYの列挙型
- Game
  - 責任：
    - ターンの管理
    - 勝敗・引き分けの判定
    - ユーザー入力の受け取り（座標）
    - 盤面の表示
    - ゲームメッセージの表示（勝者、エラーなど）
  - データ：
    - Board（盤面）
    - 2人の Player
    - 現在のプレイヤー

### Relationships

- Game has a
  - Board
  - Player x2
  - Player
- Board consists of a
  - Symbol x9
- Player has a
  - Symbol

## 3. Class Design

### Class Diagram (Text-based)

### Class Responsibilities

## 4. Implementation

### Design Patterns Used

### Key Decisions

## 5. Exception Handling

### Custom Exceptions

### Error Scenarios
