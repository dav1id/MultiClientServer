I'm going to use JavaFX first to create a small GUI that is going to ask for a name and password of the group chat that they want to join.
As long as the server is open different clients are going to be able to connect to it. The server is going to be the first client
and is going to act as a 'router' and redirect traffic to all of the clients currently connected to the server.

So I'm creating a group chat with different clients.

- Need a way to visualize the group chat (JavaFX)
- Connecting to the group chat when they're under the same network (so don't have to deal with IPs, just sending frames with
MAC addresses already defined in the mac table of the nearest switch. So I'm assuming I'm going to have far less problems here)
- Connecting to the group chat when they're under different networks (It would be cool for me to be able to make a lot of the
networking background stuff by myself. So the sending of an arp request to get the MAC address and sending the IP over different
router until it reaches its destination)


So this project is supposed to be a combination of trying to improve on my Java skills and networking ideas that I've recently learned.
Might have to switch to C/C++ if this project is too low-level for Java.
