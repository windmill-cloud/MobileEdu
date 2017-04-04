/**
 * Created by xuanwang on 4/1/17.
 */
import React from 'react';
import { View, Text } from 'react-native';


class ChatScreen extends React.Component {
    static navigationOptions = {
        title: 'Chat with Lucy',
    };

    render() {
        return (
            <View>
                <Text>Chat with Lucy</Text>
            </View>
        );
    }
}

export default ChatScreen;