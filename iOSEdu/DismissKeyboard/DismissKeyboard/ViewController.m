//
//  ViewController.m
//  DissmissKeyboard
//
//  Created by Xuan Wang on 2/5/17.
//  Copyright © 2017 a. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (IBAction)setText:(id)sender {
    self.label.text = @"Hello";
    self.textView.text = self.textField.text;
    
    // dismissing the first view over our app
    [self resignFirstResponder];
}

- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text{
    
    if([text rangeOfCharacterFromSet:[NSCharacterSet newlineCharacterSet]].location == NSNotFound){
        return YES;
    }
    [textView resignFirstResponder];
    return NO;
        
}

@end
