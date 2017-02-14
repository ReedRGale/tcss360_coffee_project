ENDPOINT=https://aqueous-caverns-35252.herokuapp.com/tcss360/messages
curl -X PUT -H "Content-Type: application/json" -d @./update_msg.json $ENDPOINT
echo