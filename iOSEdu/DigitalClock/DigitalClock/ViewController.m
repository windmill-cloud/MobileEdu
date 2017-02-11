//
//  ViewController.m
//  DigitalClock
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
    self.label.text = @"";
    timer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(updateClock) userInfo:nil repeats:YES];
    self.segmentClock.tintColor = [UIColor grayColor];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void) updateClock {
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"hh:mm:ss"];
    self.label.text = [formatter stringFromDate:[NSDate date]];
}


- (IBAction)clockColor:(id)sender {
    switch (self.segmentClock.selectedSegmentIndex) {
        case 0:
            self.label.textColor = [UIColor whiteColor];
            self.segmentClock.tintColor = [UIColor grayColor];
            break;
        case 1:
            self.label.textColor = [UIColor blackColor];
            self.segmentClock.tintColor = [UIColor blackColor];

            break;
        case 2:
            self.label.textColor = [UIColor redColor];
            self.segmentClock.tintColor = [UIColor redColor];
            break;
        case 3:
            self.label.textColor = [UIColor greenColor];
            self.segmentClock.tintColor = [UIColor greenColor];
            break;
        default:
            break;
    }
}

- (IBAction)backgroundColor:(id)sender {
    switch (self.segmentBackground.selectedSegmentIndex) {
        case 0:
            self.view.backgroundColor = [UIColor blackColor];
            self.segmentBackground.tintColor = [UIColor blackColor];
            break;
        case 1:
            self.view.backgroundColor = [UIColor whiteColor];
            self.segmentBackground.tintColor = [UIColor grayColor];
            break;
            
        case 2:
            self.view.backgroundColor = [UIColor blueColor];
            self.segmentBackground.tintColor = [UIColor blueColor];
            break;
        case 3:
            self.view.backgroundColor = [UIColor yellowColor];
            self.segmentBackground.tintColor = [UIColor yellowColor];
            break;
            
        default:
            break;
    }
}

- (IBAction)setting:(id)sender {
    if(self.settingView.hidden == NO){
        self.settingView.hidden = YES;
        self.settingButton.alpha = 0.25;
    } else {
        self.settingView.hidden = NO;
        self.settingButton.alpha = 1.0;
    }
    
}
@end
