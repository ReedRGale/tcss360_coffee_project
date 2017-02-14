ENDPOINT=https://shrouded-shore-30021.herokuapp.com/tcss360/users
curl -X POST -H "Content-Type: application/json" -d @./test_user.json $ENDPOINT
echo