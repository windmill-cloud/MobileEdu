/**
 * Created by xuanwang on 3/29/17.
 */
import React from 'react';
import { View, Text, Button } from 'react-native';

class CoreApp extends Component {

    _buttonClickHandler(){

    }

    render() {
        return (
            <View style={containerStyle}>
                <Text>Hello</Text>
                <Button title="Button" onPress={this._buttonClickHandler}/>
            </View>
        );
    }
}

const styles = {
    containerStyle: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'space-around',
    }
};

export default CoreApp;