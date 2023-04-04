
// // Create and deploy your first functions
// // https://firebase.google.com/docs/functions/get-started
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

//imports
const functions = require("firebase-functions");

const admin = require("firebase-admin");

admin.initializeApp(functions.config().firebase);

exports.sendNotificationOnThreshold = functions.database.ref("/SenData/moisture")
	.onUpdate((change, context) => {
		const newValue = change.after.val();
		if(newValue < 20){
		const message = {
			notification: {
				title: "Your plants need Attention!",
				body: "water level is below optimal for <insert plant>!"				      },
			topic: "plants_level"
		  };
		return admin.messaging().send(message)
		.then((repsonse) => {
			console.log("Message sent:",  response);
			return null;
				    })
		.catch((error) => {
			console.error("Error sending the message: ", error);
			return null;
		});
	}
		return null;
});
