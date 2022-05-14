import shutil
from coxbuild.schema import task, group, named, run, depend, withConfig, Configuration, ext
from pathlib import Path
from build.utils import root, prun, projects

testGroup = group("test")


@testGroup
@withConfig
@task
def project(config: Configuration):
    def onetest(name: str):
        subdir = root / name
        print(f"Testing {subdir.name}")
        prun(["gradle", "test"], cwd=subdir)

    name: str = config.get("name") or ""
    if not name:
        for project in projects():
            onetest(project)
    else:
        onetest(name)
