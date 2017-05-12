/**
 * Created by xuanwang on 5/10/17.
 */

import React, { Component } from 'react';
import { Provider } from 'react-redux';
import SplashScreen from "./components/SplashScreen";

class Monimenta extends Component {

    componentWillMount() {
        /*
        const config = {
            apiKey: 'AIzaSyBR4FTGiOcTZ5OBNi6thE0KhCDR3PaOtQM',
            authDomain: 'manager-2328b.firebaseapp.com',
            databaseURL: 'https://manager-2328b.firebaseio.com',
            storageBucket: 'manager-2328b.appspot.com',
            messagingSenderId: '193075244299'
        };

        firebase.initializeApp(config);
        */
    }

    render() {
        return (
            <SplashScreen />
        );
    }
}

export default Monimenta;