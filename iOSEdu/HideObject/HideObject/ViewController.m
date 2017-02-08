//
//  ViewController.m
//  HideObject
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


- (IBAction)hide:(id)sender {
    self.label.hidden = YES;
    self.segmentSwitch.hidden = YES;
    self.switchOutlet.hidden = YES;
}
- (IBAction)reveal:(id)sender {
    self.label.hidden = NO;
    self.segmentSwitch.hidden = NO;
    self.switchOutlet.hidden = NO;
}
@end
