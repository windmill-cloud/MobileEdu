/**
 * Created by xuanwang on 5/10/17.
 */

import React, { Component } from 'react';
import { Navigator } from 'react-native';
import { Provider } from 'react-redux';
import SplashScreen from './components/SplashScreen';
import LoginForm from './components/LoginForm';

class Monimenta extends Component {

    constructor (props) {
        super(props);
        this.state = { screen: 2 };
        this.state.transfer = {};
    }

    _renderScene = ( route, navigator ) => {
        _navigator = navigator;
        switch ( route.id ) {
            case 'SplashScreen':
                return(<SplashScreen navigator={navigator} title='First' />);
                break;
            case 'LoginForm':
                return(<LoginForm navigator={navigator} title="second" />);
                break;
            // case 'Card':
            //   return <Card navigator={navigator} title=''/>
            // break;
        }
    }

    render() {
        return (
            <Navigator
                initialRoute={{
                    id: 'SplashScreen'
                }}
                renderScene={
                    this._renderScene
                }
            />
        );
    }
}

export default Monimenta;