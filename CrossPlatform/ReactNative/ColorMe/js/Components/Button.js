/**
 * Created by xuanwang on 2/6/17.
 */
import React, { Component }  from 'react';
import { View, Text, TouchableOpacity } from 'react-native';

class Button extends Component{
    state = {color: "white", colored: false};

    colors = {
        "red": "#FF0000",
        "green": "#00FF00",
        "blue": "#0000FF",
        "white": "#FFFFFF"
    };

    componentWillMount() {
        styles.buttonStyle.backgroundColor = this.colors.white;
        this.setState({color: this.props.setColor});
    }

    updateColor(){
        if(this.state.colored){
            styles.buttonStyle.backgroundColor = this.colors.white;
        } else {
            styles.buttonStyle.backgroundColor = this.colors[this.state.color];
        }
        this.setState({colored: !this.state.colored});
        console.log(this.state.colored);
    }

    render() {
        console.log(this.props.children);
        var {containerStyle, textStyle, buttonStyle} = styles;
        return (
            <View style={containerStyle}>
                <TouchableOpacity onPress={() => { this.updateColor() }} style={buttonStyle}>
                    <Text style={textStyle}>
                        {this.props.children}
                    </Text>
                </TouchableOpacity>
            </View>
        );
    }
}

var styles = {
    textStyle:{
        flex: 1,
        alignSelf: 'center',
        color: '#222',
        fontSize: 16,
        fontWeight: '600',
        paddingTop: 10,
        paddingBottom: 10,
        textAlign: "center",
        textAlignVertical: "center"
    },
    buttonStyle: {
        flex: 1,
        alignSelf: 'stretch'
    },
    containerStyle: {
        flex: 1,
        backgroundColor: '#fff',
        justifyContent: 'flex-start',
        flexDirection: 'row',
        borderColor: '#FFFFFFFF',
        position: 'relative'
    }
};

export default Button;