/**
 * Created by xuanwang on 5/11/17.
 */

import React, { Component } from 'react';
import { Text, View } from 'react-native';
import { Card, Button } from './common';

class SplashScreen extends Component {

    render() {

        return (
            <View style={styles.containerStyle}>
                <View/>

                <View style={styles.buttonContainerStyle}>
                    <Button onPress={() =>
                        this.props.navigator.push({
                            id: 'LoginForm'
                        })}>
                        Login
                    </Button>
                    <Button>
                        Sign up
                    </Button>
                </View>
            </View>
        );
    }
}

const styles = {
    buttonStyle: {
        flex: 1,
        alignSelf: 'stretch',
    },
    buttonContainerStyle: {
        //flex: 1,
        flexDirection: 'row',
    },
    containerStyle: {
        flex: 1,
        backgroundColor: '#fff',
        flexDirection: 'column',
        justifyContent: 'space-between',
        borderColor: '#FFFFFFFF',
        position: 'relative',
        padding: 10
    }
};

export default SplashScreen;