ENDPOINT=https://shrouded-shore-30021.herokuapp.com/tcss360/users
curl -X DELETE -H "Content-Type: application/json" -d @./delete_user.json $ENDPOINT
echo
