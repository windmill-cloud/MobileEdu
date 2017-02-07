//
//  ViewController.m
//  ColorMe
//
//  Created by Xuan Wang on 2/6/17.
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


- (IBAction)setRed:(id)sender {
    if(self.redButton.backgroundColor == [UIColor redColor]){
        [self.redButton setBackgroundColor:[UIColor whiteColor]];
    } else {
        [self.redButton setBackgroundColor:[UIColor redColor]];
    }
}

- (IBAction)setGreen:(id)sender {
    if(self.greenButton.backgroundColor == [UIColor greenColor]){
        [self.greenButton setBackgroundColor:[UIColor whiteColor]];
    }
    else {
        [self.greenButton setBackgroundColor:[UIColor greenColor]];
    }
}

- (IBAction)setBlue:(id)sender {
    if(self.blueButton.backgroundColor == [UIColor blueColor]){
        [self.blueButton setBackgroundColor:[UIColor whiteColor]];
    }
    else {
        [self.blueButton setBackgroundColor:[UIColor blueColor]];
    }
}
@end
