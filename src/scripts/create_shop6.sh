ENDPOINT=https://shrouded-shore-30021.herokuapp.com/shops
curl -X POST -H "Content-Type: application/json" -d @./test_shops/6.json $ENDPOINT
echo