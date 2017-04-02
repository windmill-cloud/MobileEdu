/**
 * Created by xuanwang on 4/1/17.
 */
import React from 'react';
import { View, Text, Button } from 'react-native';

class MainScreen extends React.Component {

    static navigationOptions = {
        title: 'Welcome',
    };

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View>
                <Text>Hello</Text>
                <Button title="Button" onPress={ () => navigate('Chat') }/>
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

export default MainScreen;