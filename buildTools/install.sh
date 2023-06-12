#!/bin/bash
rm -rf /home/sam/NanoleafController/*
cp /home/sam/eclipse-workspace/NanoleafController/html/* /home/sam/NanoleafController
sudo mv /home/sam/eclipse-workspace/NanoleafController/buildTools/nlserver.jar /home/sam/NanoleafController
sshpass -p 'nanoleaf' ssh nanoleaf@10.2.7.84 '/home/nanoleaf/nlserver/install'
echo 'BUILD SUCCESSFUL'
