
## how to run

1. run remote actor

`sbt trackingServer/run`

## 1st party

（予め、uryyyyyyy.shake-freek.comのCookieを消しておく）

- `http://uryyyyyyy.shake-freek.com/cookieTrackingSample/static/1st_party_tracking.html`にアクセス
- localhostのサーバーからのjsが読まれてcookieを作る。
- jsがサーバーへTrackingIDをtrackingIDというパラメータで送る
- サーバーは受け取ったら標準出力で内容を確認する。
- リロードしても同じIDでトラッキングできている。

## 3rd party

（予め、localhostのCookieを消しておく）

- `http://uryyyyyyy.shake-freek.com/cookieTrackingSample/static/3rd_party_tracking.html`にアクセス
- localhostのトラッキングピクセルを読むときにSet-Cookieが送られる。
- 次回以降のアクセスの時にCookieが送られる。
- サーバーは受け取ったら標準出力で内容を確認する。