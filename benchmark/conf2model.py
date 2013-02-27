#!/usr/bin/env python3
'''
Created on Feb 19, 2013

@author: kolesnik
'''
import sys, os

model_ext = '.model'

def main(conf_paths):
    for conf_path in conf_paths:
        conf_path_no_ext = os.path.splitext(conf_path)[0] 
        model_path = conf_path_no_ext + model_ext
        if os.path.isfile(model_path):
            print('Error: file already exists.  The configuration is omitted. ' + model_path, file=sys.stderr)
            continue
        with open(model_path, mode='w') as model:
            product_name = os.path.basename(conf_path_no_ext)
            with open(conf_path) as conf:
                features = ' '.join(conf.read().splitlines())
                print(product_name + " : " + features + " :: _" + product_name + " ;", file=model)

if __name__ == "__main__":
    main(sys.argv[1:])
