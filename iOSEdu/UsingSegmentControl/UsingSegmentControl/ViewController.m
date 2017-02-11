//
//  ViewController.m
//  UsingSegmentControl
//
//  Created by Xuan Wang on 2/10/17.
//  Copyright © 2017 a. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    self.label.text = @"First";

}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (IBAction)segmentAction:(id)sender {
    switch (self.segmentControl.selectedSegmentIndex) {
        case 0:
            self.label.text = @"First";
            break;
        case 1:
            self.label.text = @"Second";
            break;
        case 2:
            self.label.text = @"Third";
            break;
        case 3:
            self.label.text = @"Forth";
            break;
            
        default:
            self.label.text = @"";
            break;
    }
    
}
@end
