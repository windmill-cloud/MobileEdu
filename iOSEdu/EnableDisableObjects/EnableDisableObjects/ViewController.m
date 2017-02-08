//
//  ViewController.m
//  EnableDisableObjects
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


- (IBAction)enable:(id)sender {
    self.label.enabled = YES;
    self.segment.enabled = YES;
    self.switchOutlet.enabled = YES;
}

- (IBAction)disable:(id)sender {
    self.label.enabled = NO;
    self.segment.enabled = NO;
    self.switchOutlet.enabled = NO;
}
@end
