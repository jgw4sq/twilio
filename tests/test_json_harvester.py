from __future__ import unicode_literals

import json

from nameparser import HumanName
from dateutil.parser import parse

from scrapi.base import JSONHarvester
from scrapi.linter import RawDocument

expected = {
    "description": "This is a  test",
    "contributors": [
        {
            "name": "Testy Testerson",
            "givenName": "Testy",
            "familyName": "Testerson",
            "additionalName": "",
            "ORCID": None,
            "email": ""
        },
        {
            "name": "Test Testerson Jr",
            "givenName": "Test",
            "familyName": "Testerson",
            "additionalName": "",
            "ORCID": None,
            "email": ""
        }
    ],
    "title": "Test",
    'uris': {
        "canonicalUri": "http://example.com"
    },
    "providerUpdatedDateTime": "2015-02-02T00:00:00",
    "shareProperties": {
        "source": "crossref"
    }
    # "otherProperties": {
    #     "referenceCount": "7",
    #     "updatePolicy": "No",
    #     "depositedTimestamp": "right now",
    #     "Empty": "",
    #     "Empty2": ""
    # }
}

def process_contributor(author, orcid):
    name = HumanName(author)
    return {
        'name': author,
        'givenName': name.first,
        'additionalName': name.middle,
        'familyName': name.last,
        'email': '',
        'ORCID': orcid
    }


class TestHarvester(JSONHarvester):
    short_name = 'crossref'
    long_name = 'CrossRef'
    url = 'http://www.crossref.org'

    DEFAULT_ENCODING = 'UTF-8'

    record_encoding = None

    @property
    def schema(self):
        return {
            'title': ('/title', lambda x: x[0] if x else ''),
            'description': ('/subtitle', lambda x: x[0] if (isinstance(x, list) and x) else x or ''),
            'providerUpdatedDateTime': ('/issued/date-parts', lambda x: parse(' '.join([part for part in x[0]])).isoformat().decode('utf-8')),
            'uris': {
                'canonicalUri': '/URL'
            },
            'contributors': ('/author', lambda x: [
                process_contributor(*[
                    '{} {}'.format(entry.get('given'), entry.get('family')),
                    entry.get('ORCID')
                ]) for entry in x
            ]),
            # 'otherProperties': {
            #     'referenceCount': '/reference-count',
            #     'updatePolicy': '/update-policy',
            #     'depositedTimestamp': '/deposited/timestamp',
            #     'Empty': '/trash/not-here',
            #     'Empty2': '/'
            # }
        }

    def harvest(self, days_back=1):
        return [RawDocument({
            'doc': str(json.dumps({
                'title': ['Test', 'subtitle'],
                'subtitle': 'This is a  test',
                'issued': {
                    'date-parts': [['2015', '2', '2']]
                },
                'DOI': '10.10123/232ff',
                'URL': 'http://example.com',
                'author': [{
                    'family': 'Testerson',
                    'given': 'Testy'
                }, {
                    'family': 'Testerson Jr',
                    'given': 'Test'
                }],
                'subject': ['Testing'],
                'container-title': ['JSON tests'],
                'reference-count': '7',
                'update-policy': 'No',
                'deposited': {
                    'timestamp': 'right now'
                },
                'trash': ''
            })),
            'source': 'crossref',
            'filetype': 'json',
            'docID': '1'
        })]


class TestJSONHarvester(object):

    def setup_method(self, method):
        self.harvester = TestHarvester()

    def test_normalize(self):
        result = [
            self.harvester.normalize(record) for record in self.harvester.harvest()
        ][0]

        for key in result.attributes.keys():
            assert result[key] == expected[key]