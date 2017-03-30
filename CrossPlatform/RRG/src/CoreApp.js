/**
 * Created by xuanwang on 3/29/17.
 */
import React from 'react';
import { View } from 'react-native';

const CoreApp = () => {
    const {containerStyle} = styles;
    return (
        <View style={containerStyle}>
        </View>
    );
};

const styles = {
    containerStyle: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'space-around',
    }
};

export default CoreApp;