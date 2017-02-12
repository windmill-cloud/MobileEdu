//
//  ViewController.m
//  HttpRequests
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


- (IBAction)getContent:(id)sender {
    NSURL *url = [NSURL URLWithString:self.textInput.text];
    NSURLRequest *urlRequest = [NSURLRequest requestWithURL:url];
    NSURLSessionDataTask *task =
    [[NSURLSession sharedSession] dataTaskWithRequest:urlRequest
                                    completionHandler:^(NSData *data,
                                                        NSURLResponse *response,
                                                        NSError *error) {
                                        // Code to run when the response completes...
                                        dispatch_sync(dispatch_get_main_queue(), ^{
                                            /* Do UI work here */
                                            self.contentView.text = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
                                        });
                                    }];
    [task resume];
}
@end
