//
//  ViewController.m
//  SetProperties
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


- (IBAction)setColor:(id)sender {
    self.label.textColor = [UIColor redColor];
}

- (IBAction)setBackground:(id)sender {
    self.label.backgroundColor = [UIColor blackColor];
}

- (IBAction)setFontSize:(id)sender {
    [self.label setFont:[UIFont fontWithName:@"Verdana" size:30]];
}

- (IBAction)setShadow:(id)sender {
    self.label.layer.shadowColor = [[UIColor blackColor] CGColor];
    self.label.layer.shadowOpacity = 0.5;
    self.label.layer.shadowRadius = 1.0f;
    self.label.layer.shadowOffset = CGSizeMake(2, 2);
}

- (IBAction)setShadowColor:(id)sender {
    self.label.layer.shadowColor = [[UIColor blueColor] CGColor];

}

- (IBAction)setLeft:(id)sender {
    self.label.textAlignment = NSTextAlignmentLeft;
}

- (IBAction)setRight:(id)sender {
    self.label.textAlignment = NSTextAlignmentRight;
}

- (IBAction)setCenter:(id)sender {
    self.label.textAlignment = NSTextAlignmentCenter;
}
@end
