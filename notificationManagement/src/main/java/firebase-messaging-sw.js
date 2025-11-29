// firebase-messaging-sw.js
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-app.js";
import { getMessaging, onBackgroundMessage } from "https://www.gstatic.com/firebasejs/9.23.0/firebase-messaging.js";

// Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyC5DI7fkR5wabcjcp8U7Pij_pd2Emm48BI",
    authDomain: "issatso-31987.firebaseapp.com",
    projectId: "issatso-31987",
    storageBucket: "issatso-31987.appspot.com",
    messagingSenderId: "749303883567",
    appId: "1:749303883567:web:d61e206c6b9ffb0d9e63da",
    measurementId: "G-VG8BMBVZHQ"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

// Handle background messages
onBackgroundMessage(messaging, (payload) => {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification handling
    if (payload.notification) {
        const { title, body } = payload.notification;
        self.registration.showNotification(title, { body, icon: '/path/to/icon.png' });
    } else if (payload.data) {
        const { title, body } = payload.data;
        self.registration.showNotification(title, { body, icon: '/path/to/icon.png' });
    }
});