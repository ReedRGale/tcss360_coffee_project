ENDPOINT=https://shrouded-shore-30021.herokuapp.com/tcss360/users
curl -X PUT -H "Content-Type: application/json" -d @./update_user.json $ENDPOINT
echo

