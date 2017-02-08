//
//  ViewController.m
//  RandomGenerator
//
//  Created by Xuan Wang on 2/8/17.
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


- (IBAction)randomNumber:(id)sender {
    
    int randomNum = arc4random() % 100;
    self.label.text = [NSString stringWithFormat:@"%i", randomNum];
}
@end
