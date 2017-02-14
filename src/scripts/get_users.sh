#ENDPOINT=https://glacial-anchorage-84282.herokuapp.com/tcss360/users
# local web runner deploy
#ENDPOINT=http://localhost:8080/tcss360/users
# net beans deploy
#ENDPOINT=http://localhost:8084/sample_maven_web_app/tcss360/users
# manual deploy
ENDPOINT=https://aqueous-caverns-35252.herokuapp.com/tcss360/users
curl -X GET -H "Content-Type: application/html" $ENDPOINT
echo

