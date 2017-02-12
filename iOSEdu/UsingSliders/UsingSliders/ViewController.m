//
//  ViewController.m
//  UsingSliders
//
//  Created by Xuan Wang on 2/11/17.
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


- (IBAction)fontSize:(id)sender {
    [self.label setFont:[UIFont fontWithName:@"Verdana" size:self.slider.value]];
    self.fontValLabel.text = [NSString stringWithFormat:@"%.0f", self.slider.value];
}
@end
