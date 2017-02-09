//
//  ViewController.m
//  RandomFacts
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
    [self.label.layer setCornerRadius:20.0];
    self.label.clipsToBounds = YES;
    self.label.text = @"";
    self.label.hidden = YES;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (IBAction)randomFact:(id)sender {
    if(self.label.hidden == YES){
        self.label.hidden = NO;
    }
    NSArray *facts = @[@"Banging your head against a wall burns 150 calories an hour."
                       ,@"When hippos are upset, their sweat turns red."
                       ,@"A flock of crows is known as a murder."
                       ,@"Human saliva has a boiling point three times that of regular water."];
    
    int randomWord = arc4random() % facts.count;
    self.label.text = facts[randomWord];
}
@end
