
## cookie tracking(& CORS) sample

1. サーバーの起動

`sbt trackingServer/run`

## 1st party

（予め、uryyyyyyy.shake-freek.comのCookieを消しておく）

- `https://opt-tech.github.io/cookieTrackingSample/static/1st_party_tracking.html`にアクセス
- localhostのサーバーからのjsが読まれてcookieを作る。
- jsがサーバーへTrackingIDをtrackingIDというパラメータで送る
- サーバーは受け取ったら標準出力で内容を確認する。
- リロードしても同じIDでトラッキングできている。

## 3rd party

（予め、localhostのCookieを消しておく）

- `https://opt-tech.github.io/cookieTrackingSample/static/3rd_party_tracking.html`にアクセス
- localhostのトラッキングピクセルを読むときにSet-Cookieが送られる。
- 次回以降のアクセスの時にCookieが送られる。
- サーバーは受け取ったら標準出力で内容を確認する。

## no pre flight

- `https://opt-tech.github.io/cookieTrackingSample/static/ajax_no_pre_flight.html`にアクセス
- htmlのajaxの中で外部ドメインへのapplication/x-www-form-urlencodedのリクエストを出しているので、pre flightなし。
- POSTリクエストを投げる
- 処理されて文字列が返ってくるので、それをajaxのcallbackで処理する。

## pre flight

- `https://opt-tech.github.io/cookieTrackingSample/static/ajax_pre_flight.html`にアクセス
- htmlのajaxの中で外部ドメインへのapplication/jsonのリクエストを出しているので、pre flightが走る
- サーバ側でOPTIONSメソッドに対して対象ドメインを許可する。
- pre flightが通ったので、改めてPOSTリクエストを投げる
- 処理されて文字列がブラウザに返ってくる。
- ヘッダの情報から、ブラウザで処理しても問題ないと判断してjsが処理する。ここではalertの文言を出す。

## pre flight2

- `https://opt-tech.github.io/cookieTrackingSample/static/ajax_pre_flight2.html`にアクセス
- htmlのajaxの中で、独自ヘッダ（X-Requested-With）を付けて外部ドメインへリクエストを出しているので、pre flightが走る
- サーバ側でOPTIONSメソッドに対して対象ドメインを許可する。
- pre flightが通ったので、改めてPOSTリクエストを投げる
- 処理されて文字列がブラウザに返ってくる。
- ヘッダの情報から、ブラウザで処理しても問題ないと判断してjsが処理する。ここではalertの文言を出す。