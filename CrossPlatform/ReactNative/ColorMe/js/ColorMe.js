/**
 * Created by xuanwang on 2/6/17.
 */
import React from 'react';
import { View } from 'react-native';
import Button from './Components/Button';

const ColorMe = () => {
    const {containerStyle} = styles;
    return (
        <View style={containerStyle}>
            <Button setColor="red">Red</Button>
            <Button setColor="green">Green</Button>
            <Button setColor="blue">Blue</Button>
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

export default ColorMe;