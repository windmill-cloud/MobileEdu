//
//  ViewController.m
//  DisplayDateTime
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
    timer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(updateTimer) userInfo:nil repeats:YES];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)updateTimer {
    NSDateFormatter *timerFormatter = [[NSDateFormatter alloc] init];
    [timerFormatter setDateFormat:@"hh:mm:ss"];
    self.timeLabel.text = [timerFormatter stringFromDate:[NSDate date]];
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"MM-dd-YYYY"];
    self.dateLabel.text = [dateFormatter stringFromDate:[NSDate date]];
}


@end
