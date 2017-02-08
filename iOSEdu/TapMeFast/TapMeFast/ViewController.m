//
//  ViewController.m
//  TapMeFast
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
    timeInt = 10;
    tapInt = 0;
    
    self.timeLabel.text = [NSString stringWithFormat:@"%i", timeInt];
    self.scoreLabel.text = [NSString stringWithFormat:@"%i", tapInt];
    self.tapMeButton.enabled = NO;
    self.tapMeButton.alpha = 0.5;
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (IBAction)tapButton:(id)sender {
    tapInt += 1;
    self.scoreLabel.text = [NSString stringWithFormat:@"%i", tapInt];
    
}

- (IBAction)startGame:(id)sender {
    
    if(timeInt == 10){
        timer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(startCounter) userInfo:nil repeats:YES];
        self.tapMeButton.enabled = YES;
        self.tapMeButton.alpha = 1.0;
        
        self.startGameButton.enabled = NO;
        self.startGameButton.alpha = 0.5;
    }
    
    if(timeInt == 0){
        timeInt = 10;
        tapInt = 0;
        self.timeLabel.text = [NSString stringWithFormat:@"%i", timeInt];
        self.scoreLabel.text = [NSString stringWithFormat:@"%i", tapInt];
        [self.startGameButton setTitle:@"Start" forState:UIControlStateNormal];
    }
}

- (void) startCounter {
    timeInt -= 1;
    self.timeLabel.text = [NSString stringWithFormat:@"%i", timeInt];
    if(timeInt == 0){
        [timer invalidate];
        self.tapMeButton.enabled = NO;
        self.tapMeButton.alpha = 0.5;
        
        self.startGameButton.enabled = YES;
        self.startGameButton.alpha = 1.0;
        
        [self.startGameButton setTitle:@"Reset" forState:UIControlStateNormal];
    }
}
@end
