if Redis.call("get",key==argv[1])then
	return Redis.call("del",key)
else return 0 end