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

```
Symbol (enum)
--------
X, O, EMPTY
--------
+ toString(): String


Player
--------
- name: String
- symbol: Symbol
--------
+ Player(name: String, symbol: Symbol)
+ getName(): String
+ getSymbol(): Symbol


Board
--------
- grid: Symbol[][]
--------
+ Board()
+ makeMove(row: int, col: int, symbol: Symbol): boolean
+ checkWinner(): Symbol
+ isFull(): boolean
+ display(): void


Game
--------
- board: Board
- players: Player[]  // 2人
- currentPlayerIndex: int
--------
+ Game(player1: Player, player2: Player)
+ play(): void  // main loop
- switchPlayer(): void
- getPlayerInput(): String
- parseCoordinate(input: String): int[]  // "2c" → [1, 2]
```

### Class Responsibilities

Symbol
- ゲーム盤面の状態を表現（X, O, 空）
- 表示用の文字列に変換

Player
- プレイヤー情報を保持
- 不変（immutable）なデータクラス

Board
- 3x3グリッドの状態管理
- 手の妥当性チェックと実行
- 勝利条件の判定
- 盤面の表示

Game
- ゲーム全体のフロー制御
- プレイヤーのターン管理
- ユーザー入力の処理
- 座標パース（"2c" → 配列インデックス）

## 4. Implementation Notes

### Design Patterns Used

- Single Responsibility Principle

### Key Decisions

1. 座標入力: "2c" 形式（行: 1-3、列: a-c）
   - String parsing が必要
2. Grid の内部表現: 0-indexed の2次元配列
   - ユーザー入力（1-3, a-c）-> 内部用のインデックス（0-2）への変換が必要
3. 勝利判定: Board クラスで実装
   - 横3行、縦3列、斜め2方向をチェック
4. I/O 処理: Game クラスに含める
   - 将来的に別クラスに分離可能

## 5. Exception Handling Strategy

### Error Scenarios

1. 無効な座標入力: 範囲外（4d など）、形式エラー（abc など）
2. 既に埋まっているマス: 同じ場所に手を打とうとする
3. 入力形式エラー: "2c" 以外の形式

対応方法
- エラーメッセージを表示して再入力を促す
- ゲームは中断せず続行
