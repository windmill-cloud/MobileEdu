//
//  ViewController.m
//  UsingNSTimer
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


- (IBAction)startTimer:(id)sender {
    countInt = 0;
    self.label.text = [NSString stringWithFormat:@"%i", countInt];
    
    timer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(updateTimer) userInfo:nil repeats:YES];
}

- (IBAction)stopTimer:(id)sender {
    [timer invalidate];
}



-(void)updateTimer {
    countInt++;
    self.label.text = [NSString stringWithFormat:@"%i", countInt];
}
@end
