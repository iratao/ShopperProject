#!/usr/bin/python
# -*- coding: utf-8 -*-

from sys import argv
import json
import csv
from pprint import pprint
import codecs

import sys
reload(sys) 
sys.setdefaultencoding('utf-8')

jsonFileName = argv[1]

pprint(jsonFileName)

with codecs.open(jsonFileName, encoding='UTF-8') as data_file:    
    jsondata = json.load(data_file)

output_csv = codecs.open(jsonFileName + '.csv', 'w', encoding='UTF-8')
csvwriter = csv.writer(output_csv)
csvwriter.writerow(['id', 'name', 'price', 'brand', 'color', 'contenttype', 'style', 'image_url'])

items = jsondata["items"]
for item in items:
	color = ''
	contenttype = ''
	style = ''
	for attribute in item['attributes']:
		if attribute['name'] == 'Colors':
			color = attribute['values'][0]['value']
		elif attribute['name'] == 'ContentType':
			contenttype = attribute['values'][0]['value']
		elif attribute['name'] == 'Styles':
			style = attribute['values'][0]['value']

		price = 0.0;
		if len(item['retailers']) > 0 and item['retailers'][0].has_key('price'):
			price = item['retailers'][0]['price']
		
		imageurl = '';
		if len(item['images']) > 0:
			imageurl = item['images'][0]

	csvwriter.writerow([item['id'], item['name'], price, item['vendor'], color, contenttype, style, imageurl])

