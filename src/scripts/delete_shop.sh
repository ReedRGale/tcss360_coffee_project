ENDPOINT=https://shrouded-shore-30021.herokuapp.com/shops
curl -X DELETE -H "Content-Type: application/json" -d @./test_shops/delete_this.json $ENDPOINT
echo
