# StudentRepositoryテスト

## 目的

`StudentRepository`のMyBatisマッピングとSQLが正しく動作することを確認するテストです。
本番のMySQLには接続せず、テスト実行時だけH2インメモリDBを使用します。

## 対象ファイル

- `StudentRepositoryTest.java`: Repositoryのテスト本体
- `src/test/resources/repository-schema.sql`: テスト用テーブル定義
- `src/test/resources/repository-data.sql`: テスト用初期データ

## テストデータ

- 受講生: 10件
- 受講生コース: 7件

`@Sql`で各テストの実行前にテーブルとデータを再作成するため、テスト同士は影響しません。

## 正常系

- 受講生の一覧検索
- IDを指定した受講生検索
- 受講生の登録と自動採番
- 受講生コースの一覧検索
- 受講生IDに紐づくコース検索
- 受講生コースの登録と自動採番
- 受講生情報の更新
- `isDeleted=true`による論理削除

## 該当なし・異常系

- 存在しない受講生IDの検索で`null`が返ること
- 存在しない受講生IDのコース検索で空リストが返ること
- 名前が`null`の受講生を登録できないこと
- コース名が`null`のコースを登録できないこと
- 存在しない受講生IDにコースを登録できないこと

## 実行方法

Windows:

```powershell
.\gradlew.bat test --tests raisetech.student.management2.repository.StudentRepositoryTest
```

macOS / Linux:

```bash
./gradlew test --tests raisetech.student.management2.repository.StudentRepositoryTest
```

HTMLレポートは実行後に`build/reports/tests/test/index.html`へ出力されます。

## 削除の扱い

このアプリケーションは、受講生レコードを物理的に削除しません。
`Student.isDeleted`を`true`に更新する論理削除をテストしています。

