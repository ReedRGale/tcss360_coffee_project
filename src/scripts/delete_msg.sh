ENDPOINT=https://aqueous-caverns-35252.herokuapp.com/tcss360/messages
curl -X DELETE -H "Content-Type: application/json" -d @./delete_msg.json $ENDPOINT
echo