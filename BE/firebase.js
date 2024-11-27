import firebase from "firebase/compat/app"
import {
    createUserWithEmailAndPassword,
    signInWithEmailAndPassword,
    signOut,
    sendEmailVerification,
    sendPasswordResetEmail,
    getAuth,
    updatePassword,
} from "firebase/auth";

const firebaseConfig = {
    apiKey: "AIzaSyDTDJNZ0i0hLLAZFWqYBL-SqbBTj1THzCo",
    authDomain: "banmypham-414e9.firebaseapp.com",
    databaseURL: "https://banmypham-414e9-default-rtdb.europe-west1.firebasedatabase.app",
    projectId: "banmypham-414e9",
    storageBucket: "banmypham-414e9.appspot.com",
    messagingSenderId: "678156914593",
    appId: "1:678156914593:web:477e5eb4ef67c07298c5e0",
    measurementId: "G-7DXFF5PFSB"
};

firebase.initializeApp(firebaseConfig);

const auth = getAuth();

export {
    auth,
    signInWithEmailAndPassword,
    createUserWithEmailAndPassword,
    signOut,
    sendEmailVerification,
    sendPasswordResetEmail,
    updatePassword
};