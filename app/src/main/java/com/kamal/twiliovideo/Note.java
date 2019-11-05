package com.kamal.twiliovideo;

public class Note {

    /*
    User Identity & Access Tokens
    //=========================================================
    https://www.twilio.com/docs/video/tutorials/user-identity-access-tokens#access-token-server-sample-applications
    Example: https://github.com/twilio/video-quickstart-js
    https://github.com/TwilioDevEd/video-access-token-server-node



    User Identity & Access Tokens
    //=========================================================
    https://www.youtube.com/watch?v=5lrdYBLEk60



    Building Video Applications on Android By Marcos Placona
    //=========================================================
    https://www.twilio.com/blog/2016/03/building-video-applications-on-android.html
    https://github.com/mplacona/TwilioVideo



    Note:
    //=========
    https://www.youtube.com/watch?v=PKodXk6L5Eo
    https://www.youtube.com/watch?v=lkQczBYLniA&list=PLQm9P1qjjvxvZwV3Whv0Ehz4KUNe5ugNa&index=2

    Java Library
    https://www.twilio.com/docs/libraries/java

    https://www.twilio.com/docs/runtime/functions-assets-api/quickstart#please-read-before-starting
    */


    /*
    =============================================

    public static final String ACCOUNT_SID = "ACf92291edf2e4e6b90e2b437452ed5eff";
    public static final String AUTH_TOKEN = "3e235fad509dd22c78eebefb79aa9db8";

    =============================================
    FRIENDLY NAME
    TwilioVideo

    SID
    SK638e9993ed26cea3fb4da9755b2101ed

    KEY TYPE
    Standard

    SECRET
    iR3kvfJmseBFOgA8mSIaaXEy34qAsqWU

    let accountSid = process.env.TWILIO_ACCOUNT_SID;
    let authToken = process.env.TWILIO_AUTH_TOKEN;

    let client = require('twilio')(accountSid, authToken);

    let apiKey = process.env.TWILIO_API_KEY;
    let secret = process.env.TWILIO_API_SECRET;

    let client = require('twilio')(apiKey, secret);
    ==============================================
    exports.handler = function(context, event, callback) {
        let AccessToken = Twilio.jwt.AccessToken;
        let VideoGrant = AccessToken.VideoGrant;
        let client = context.getTwilioClient();
        //console.log(client.accountSid);
        console.log(context.ACCOUNT_SID);
        console.log(context.API_KEY);
        console.log(context.API_SECRET);

        let token = new AccessToken(context.ACCOUNT_SID, context.API_KEY, context.API_SECRET);
        token.identity = event.identity;

        console.log(token);

        let videoGrant = new VideoGrant({
            room: 'DailyStandup'
        });

        token.addGrant(videoGrant);

        callback(null, {token: token.toJwt});
    };
    ================================================
    exports.handler = function(context, event, callback) {
        const AccessToken = require('twilio').jwt.AccessToken;
        const VideoGrant = AccessToken.VideoGrant;

        // Used when generating any kind of Access Token
        const twilioAccountSid = 'ACf92291edf2e4e6b90e2b437452ed5eff';
        const twilioApiKey = 'SK638e9993ed26cea3fb4da9755b2101ed';
        const twilioApiSecret = 'iR3kvfJmseBFOgA8mSIaaXEy34qAsqWU';

        // Create an access token which we will sign and return to the client,
        // containing the grant we just created
        const token = new AccessToken(twilioAccountSid, twilioApiKey, twilioApiSecret);
        token.identity = 'TwilioVideo';

        // Create a Video grant which enables a client to use Video
        // and limits access to the specified Room (DailyStandup)
        const videoGrant = new VideoGrant({
            room: 'DailyStandup'
        });

        // Add the grant to the token
        token.addGrant(videoGrant);

        // Serialize the token to a JWT string
        console.log(token.toJwt());

        console.log(context.ACCOUNT_SID);
        console.log(context.API_KEY);
        console.log(context.API_SECRET);

        callback(null, {token: token.toJwt});
    };
    =================================================
    exports.handler = function(context, event, callback) {
        const AccessToken = require('twilio').jwt.AccessToken;
        const VideoGrant = AccessToken.VideoGrant;

        // Used when generating any kind of Access Token
        const twilioAccountSid = 'ACf92291edf2e4e6b90e2b437452ed5eff';
        const twilioApiKey = 'SK638e9993ed26cea3fb4da9755b2101ed';
        const twilioApiSecret = 'iR3kvfJmseBFOgA8mSIaaXEy34qAsqWU';

        // Create an access token which we will sign and return to the client,
        // containing the grant we just created
        const token = new AccessToken(twilioAccountSid, twilioApiKey, twilioApiSecret);
        token.identity = 'TwilioVideo';

        // Create a Video grant which enables a client to use Video
        // and limits access to the specified Room (DailyStandup)
        const videoGrant = new VideoGrant({
            room: 'DailyStandup'
        });

        // Add the grant to the token
        token.addGrant(videoGrant);

        // Serialize the token to a JWT string
        console.log(token.toJwt());

        console.log(context.ACCOUNT_SID);
        console.log(context.API_KEY);
        console.log(context.API_SECRET);

        //callback(null, {token: token.toJwt});
        callback(null, { token: token.toJwt() });
    };

    */
}
